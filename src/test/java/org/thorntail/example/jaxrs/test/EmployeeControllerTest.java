package org.thorntail.example.jaxrs.test;

import static org.fest.assertions.Assertions.assertThat;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.thorntail.example.jaxrs.controller.EmployeeController;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@RunWith(Arquillian.class)
public class EmployeeControllerTest {

	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		WebArchive testArchive = ShrinkWrap.create(WebArchive.class, "EmployeeControllerTest.war")
				.addPackages(true, "org.thorntail.example.jaxrs.model", "org.thorntail.example.jaxrs.service",
						"org.thorntail.example.jaxrs.controller")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") // Required
				.addAsResource("project-defaults.yaml");
		LOGGER.info(testArchive.toString(true));
		return testArchive;
	}

	@Drone
	private WebDriver browser;

	@Test
	public void must_find_an_employee() {
		browser.navigate().to("http://localhost:8080/rest/employees" + EmployeeController.API_VERSION);
		assertThat(browser.getPageSource()).contains("{\"id\":5,\"name\":\"Bernadette\"}");
	}
}
