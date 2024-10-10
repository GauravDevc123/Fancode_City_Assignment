package Testing.Fancode_City_Assignment;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Unit test for simple App.
 */
public class FancodeUsersTest {
	// Global Parameters common to Fancode Tests
	public static RequestSpecification requestSpecUsers;
	public static RequestSpecification requestSpecTodos;
	public static RequestSpecification requestSpecPosts;
	public static RequestSpecification requestSpecComments;
	public static RequestSpecification requestSpecAlbums;
	public static RequestSpecification requestSpecPhotos;
	public final static int latMin = -40;
	public final static int latMax = 5;
	public final static int lngMin = 5;
	public final static int lngMax = 100;
	public static List<String> userIds = null;
	List<String> latitudes = null;
	List<String> longitudes = null;
	public static List<String> userIdTodos = null;
	public static List<String> userIdPosts = null;
	public static List<String> status = null;
	public static List<String> postId = null;
	public static List<String> postIdComments = null;
	public static List<String> commentId = null;
	public static List<String> userIdAlbums = null;
	public static List<String> albumId = null;
	public static List<String> albumIdPhotos = null;
	public static List<String> photoId = null;
	public static ReportingCommons reporting;

	@BeforeSuite
	//Setting the Reporter Constructor
	public void setReporter() {
		reporting = new ReportingCommons();
	}

	// Preparing end point invocation from Rest Assured for APIs
	@BeforeTest
	@Parameters({ "BaseURI", "UsersEndpoint", "TodosEndpoint", "PostsEndpoint", "CommentsEndpoint", "AlbumsEndpoint",
			"PhotosEndpoint" })
	public void ConstructAPIEndpoints(String baseUri, String usersEndpoint, String todosEndpoint, String postsEndpoint,
			String commentsEndpoint, String albumsEndpoint, String photosEndpoint) {
		RestAssured.baseURI = baseUri;
		RestAssured.useRelaxedHTTPSValidation();
		requestSpecUsers = RestAssured.given().basePath(usersEndpoint);
		requestSpecTodos = RestAssured.given().basePath(todosEndpoint);
		requestSpecPosts = RestAssured.given().basePath(postsEndpoint);
		requestSpecComments = RestAssured.given().basePath(commentsEndpoint);
		requestSpecAlbums = RestAssured.given().basePath(albumsEndpoint);
		requestSpecPhotos = RestAssured.given().basePath(photosEndpoint);
	}

	// Get List of Users from /users end point response
	@BeforeClass
	public void getUsersList() {
		Response response = requestSpecUsers.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			userIds = jsonExtractor.get("id");
			latitudes = jsonExtractor.get("address.geo.lat");
			longitudes = jsonExtractor.get("address.geo.lng");
		} else {
			assertTrue(false, "Response from /users Endpoint is not proper. Please debug");
		}
	}

	// Get List of Todos from /todos end point response
	@BeforeClass
	public void geTodosList() {
		Response response = requestSpecTodos.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			userIdTodos = jsonExtractor.get("userId");
			status = jsonExtractor.get("completed");
		} else {
			assertTrue(false, "Response from /todos Endpoint is not proper. Please debug");
		}
	}

	// Get List of Posts from /posts end point response
	@BeforeClass
	public void getPostsList() {
		Response response = requestSpecPosts.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			userIdPosts = jsonExtractor.get("userId");
			postId = jsonExtractor.get("id");
		} else {
			assertTrue(false, "Response from /posts Endpoint is not proper. Please debug");
		}
	}

	// Get List of Comments from /comments end point response
	@BeforeClass
	public void getCommentsList() {
		Response response = requestSpecComments.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			postIdComments = jsonExtractor.get("postId");
			commentId = jsonExtractor.get("idd");
		} else {
			assertTrue(false, "Response from /commnets Endpoint is not proper. Please debug");
		}
	}

	// Get List of Albums from /albums end point response
	@BeforeClass
	public void getAlbumsList() {
		Response response = requestSpecAlbums.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			userIdAlbums = jsonExtractor.get("userId");
			albumId = jsonExtractor.getList("id");
		} else {
			assertTrue(false, "Response from /commnets Endpoint is not proper. Please debug");
		}
	}

	// Get List of Photos from /photos end point response
	@BeforeClass
	public void getPhotosList() {
		Response response = requestSpecPhotos.get();
		JsonPath jsonExtractor = null;
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			jsonExtractor = response.jsonPath();
			albumIdPhotos = jsonExtractor.get("postId");
			photoId = jsonExtractor.get("id");
		} else {
			assertTrue(false, "Response from /commnets Endpoint is not proper. Please debug");
		}
	}

	//Test the Scenario for Users with Fancode City having more than 50% completed tasks
	@org.testng.annotations.Test(dataProvider = "usersData", dependsOnMethods = {
			"validateSufficientUsersForFancodeCity" })
	public void TestTodosCompletionForFancodeCityUsers(String userId, String userLatitude, String userLongitude,
			Float completedTodoPercentage) {
		reporting.createReportTest("Test User within Fancode with user id: " + userId, "Test_02");
		if ((Float.parseFloat(userLatitude) >= latMin && Float.parseFloat(userLatitude) <= latMax)
				&& (Float.parseFloat(userLongitude) >= lngMin && Float.parseFloat(userLongitude) <= lngMax)) {
			Assert.assertTrue(completedTodoPercentage >= 50,
					"User with User ID: " + userId + " has successfully completed more than 50% of his todo tasks");
			if (completedTodoPercentage >= 50) {
				reporting.passTestStep(
						"User with User ID: " + userId + " has successfully completed more than 50% of his todo tasks");
			} else {
				reporting.failTestStep(
						"User with User ID: " + userId + " has not completed more than 50% of his todo tasks");
			}
		} else {
			reporting.skipTestStep(
					"User with user ID: " + userId + " is not within Fancode city. Hence, Skipping the test Case");
			// Uncomment below line if reporting/debugging is required at TestNG level
			throw new SkipException("Data is skipped as User's Latitude: " + userLatitude + " and User's Longitude: "
					+ userLongitude + " are not within Fancode City");
		}
	}

	// Test whether sufficient data for Fancode city users is present or not
	@org.testng.annotations.Test
	public void validateSufficientUsersForFancodeCity() {
		// Uncomment below line if reporting/debugging is required at TestNG level
		assertTrue(userIds.size() > 0, "There areS sufficient users in Fancode City to execute the tests");
		reporting.createReportTest("Validate Sufficient Users are available within Fancode for testing the scenario",
				"Test_01");
		if (userIds.size() > 0) {
			reporting.passTestStep("Sufficient Records present to test the scenario for Fancode city users");
		} else {
			reporting.failTestStep("Sufficient Records are not present to test the scenario for Fancode city users");
		}
	}

	//Log Reports to extent
	@AfterSuite
	public void tearDown() {
		reporting.logReport();
	}

	// Data Provider to get metadata about all users and their geographical locations
	@DataProvider(name = "usersData")
	public Object[][] getUsersMetadata() {
		Object[][] usersLocation = new Object[userIds.size()][4];
		HashMap<String, Float> percentageOfTodosCompletedPerUser = calculatepecentageOfTodosCompletedPerUser();
		for (int userList = 0; userList < userIds.size(); userList++) {
			usersLocation[userList][0] = String.valueOf(userIds.get(userList));
			usersLocation[userList][1] = latitudes.get(userList);
			usersLocation[userList][2] = longitudes.get(userList);
			usersLocation[userList][3] = percentageOfTodosCompletedPerUser.get(String.valueOf(userIds.get(userList)));
		}
		return usersLocation;
	}
    
	// Mapping Users and their completed tasks percentage
	public static HashMap<String, Float> calculatepecentageOfTodosCompletedPerUser() {
		HashMap<String, Float> percentagePerUser = new HashMap<>();
		for (int userList = 0; userList < userIds.size(); userList++) {
			String userId = String.valueOf(userIds.get(userList));
			int total = 0;
			int completed = 0;
			for (int todosList = 0; todosList < userIdTodos.size(); todosList++) {
				if (String.valueOf(userIdTodos.get(todosList)).equals(userId)) {
					total += 1;
					if (String.valueOf(status.get(todosList)).equals("true")) {
						completed++;
					}
				}
			}
			percentagePerUser.put(userId, ((float) completed) * 100 / total);
		}
		return percentagePerUser;
	}

}