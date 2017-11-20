package applicaton;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.BankAccountDaoBean;
import entities.BankAccount;

@Path("bankAccount")
public class BankAccountResource {

	@EJB
	private BankAccountDaoBean bankAccountDaoBean;

	private List<BankAccount> bankAccountDaoBeanList;
	private JsonObject jsonObject;

	@GET
	@Path("/{UserId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getUserList(@PathParam("UserId") long userId) {
		if (userId == 1) {
			System.out.println("IsAdmin!!");
			bankAccountDaoBeanList = bankAccountDaoBean.getAccountListFree();

			JsonObjectBuilder jBuilder = Json.createObjectBuilder();
			JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

			for (BankAccount bankAccount : bankAccountDaoBeanList) {
				jArrayBuilder.add(Json.createObjectBuilder().add("id", bankAccount.getId())
						.add("number", bankAccount.getNumber()).add("description", bankAccount.getDescription())
						.add("summ", bankAccount.getSumm()).build());
			}
			jsonObject = jBuilder.add("BankAccounts", jArrayBuilder.build()).build();

		} else {
			System.out.println("IsNOTAdmin!!");
			bankAccountDaoBeanList = bankAccountDaoBean.getAccountList(userId);

			JsonObjectBuilder jBuilder = Json.createObjectBuilder();
			JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

			for (BankAccount bankAccount : bankAccountDaoBeanList) {
				jArrayBuilder.add(Json.createObjectBuilder().add("id", bankAccount.getId())
						.add("number", bankAccount.getNumber()).add("description", bankAccount.getDescription())
						.add("summ", bankAccount.getSumm()).build());
			}
			jsonObject = jBuilder.add("BankAccounts", jArrayBuilder.build()).build();
			StringWriter stringWriter = new StringWriter();
			try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
				jsonWriter.write(jsonObject);
			}
			System.out.println(stringWriter.toString());
		}
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}
		System.out.println(stringWriter.toString());
		return jsonObject;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getUserList(String request) {
		Long userId;
		System.out.println(request);
		if (request.isEmpty() != true) {
			JsonReader jsonReader = Json.createReader(new StringReader(request));
			jsonObject = jsonReader.readObject();

			userId = jsonObject.getJsonNumber("userId").longValue();
			JsonObject jsonObjectInRequest = jsonObject.getJsonObject("BankAccount");
			BankAccount bankAccount = new BankAccount();
			bankAccount.setNumber(jsonObjectInRequest.getJsonNumber("number").longValue());
			bankAccount.setDescription(jsonObjectInRequest.getString("description"));
			bankAccount.setSumm(jsonObjectInRequest.getJsonNumber("summ").longValue());
			System.out.println(
					bankAccount.getNumber() + " " + bankAccount.getSumm() + " " + bankAccount.getDescription());
			bankAccountDaoBean.saveNew(bankAccount, userId);
			jsonObject = Json.createObjectBuilder().add("Result", "1").build();
		}

		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}
		System.out.println(stringWriter.toString());
		return jsonObject;
	}

}
