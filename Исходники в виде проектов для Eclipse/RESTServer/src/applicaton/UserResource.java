package applicaton;

import java.io.StringWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.UserDaoBean;
import entities.User;

@Path("user")
public class UserResource {

	@EJB
	private UserDaoBean userDaoBean;

	private List<User> userDaoBeanList;
	private JsonObject jsonObject;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getUserList() {
		userDaoBeanList = userDaoBean.getUserList();

		JsonObjectBuilder jBuilder = Json.createObjectBuilder();
		JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

		for (User user : userDaoBeanList) {
			jArrayBuilder.add(Json.createObjectBuilder().add("id", user.getId()).add("user", user.getUser()).build());
		}
		jsonObject = jBuilder.add("Users", jArrayBuilder.build()).build();

		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}

		System.out.println(stringWriter.toString());

		return jsonObject;
	}

}
