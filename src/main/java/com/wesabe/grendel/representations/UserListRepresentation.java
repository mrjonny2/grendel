package com.wesabe.grendel.representations;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.annotate.JsonGetter;

import com.google.common.collect.Lists;
import com.wesabe.grendel.entities.User;
import com.wesabe.grendel.resources.UserResource;

/**
 * A representation of a response containing information about a list of users.
 * <p>
 * Example JSON:
 * <pre>
 * {
 *   "users":[
 *     {
 *       "uri":"http://example.com/users/codahale",
 *       "id":"codahale"
 *     }
 *   ]
 * }
 * </pre>
 * 
 * @author coda
 */
public class UserListRepresentation {
	public static class UserListItem {
		private final UriInfo uriInfo;
		private final User user;
		
		public UserListItem(UriInfo uriInfo, User user) {
			this.uriInfo = uriInfo;
			this.user = user;
		}
		
		@JsonGetter("id")
		public String getId() {
			return user.getId();
		}
		
		@JsonGetter("uri")
		public String getUri() {
			return uriInfo.getBaseUriBuilder()
							.path(UserResource.class)
							.build(user).toASCIIString();
		}
	}
	
	private final UriInfo uriInfo;
	private final List<User> users;
	
	public UserListRepresentation(UriInfo uriInfo, List<User> users) {
		this.uriInfo = uriInfo;
		this.users = users;
	}
	
	@JsonGetter("users")
	public List<UserListItem> getUsers() {
		final List<UserListItem> items = Lists.newArrayListWithExpectedSize(users.size());
		for (User user : users) {
			items.add(new UserListItem(uriInfo, user));
		}
		return items;
	}

}
