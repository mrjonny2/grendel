package com.wesabe.grendel.resources.dto.tests;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.wesabe.grendel.resources.dto.NewUserRequest;
import com.wesabe.grendel.resources.dto.ValidationException;

@RunWith(Enclosed.class)
public class NewUserRequestTest {
	public static class A_Valid_New_User_Request {
		private NewUserRequest req;
		
		@Before
		public void setup() throws Exception {
			this.req = new NewUserRequest();
			
			req.setUsername("dingo");
			req.setPassword("happenstance".toCharArray());
		}
		
		@Test
		public void itIsValid() throws Exception {
			try {
				req.validate();
				assertThat(true).isTrue();
			} catch (ValidationException e) {
				fail("didn't expect a ValidationException but one was thrown");
			}
		}
		
		@Test
		public void itHasAUsername() throws Exception {
			assertThat(req.getUsername()).isEqualTo("dingo");
		}
		
		@Test
		public void itHasAPassword() throws Exception {
			assertThat(req.getPassword()).isEqualTo("happenstance".toCharArray());
		}
		
		@Test
		public void itCanBeSanitized() throws Exception {
			assertThat(req.getPassword()).isEqualTo("happenstance".toCharArray());
			req.sanitize();
			assertThat(req.getPassword()).isEqualTo("\0\0\0\0\0\0\0\0\0\0\0\0".toCharArray());
		}
	}
	
	public static class An_Invalid_New_User_Request {
		private NewUserRequest req;
		
		@Before
		public void setup() throws Exception {
			this.req = new NewUserRequest();
		}
		
		@Test
		public void itThrowsAnExceptionOnValidation() throws Exception {
			try {
				req.validate();
				fail("should have thrown a ValidationException but didn't");
			} catch (ValidationException e) {
				final String msg = (String) e.getResponse().getEntity();
				
				assertThat(msg).isEqualTo(
					"Grendel was unable to process your request for the following reason(s):" +
					"\n" +
					"\n" +
					"* missing required property: username\n" +
					"* missing required property: password\n"
				);
			}
		}
	}
}