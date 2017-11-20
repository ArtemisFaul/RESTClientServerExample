package applicaton;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.BillingDaoBean;
import entities.Billing;

@Path("billing")
public class BillingResource {
	@EJB
	private BillingDaoBean billingDaoBean;
	private List<Billing> billingDaoBeanInputList;
	private List<Billing> billingDaoBeanOutList;
	private JsonObject jsonObject;

	@GET
	@Path("/io/{BankAccountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getInputBillingList(@PathParam("BankAccountId") long bankAccountId) {
		billingDaoBeanInputList = billingDaoBean.getBillingInputList(bankAccountId);
		JsonArrayBuilder jArrayInput = Json.createArrayBuilder();
		for (Billing billing : billingDaoBeanInputList) {
			jArrayInput.add(Json.createObjectBuilder().add("id", billing.getId())
					.add("operation", billing.getOperationTo().getName())
					.add("bankAccountFrom", billing.getBankAccountFrom().getNumber())
					.add("bankAccountTo", billing.getBankAccountTo().getNumber())
					.add("description", billing.getDescription()).add("summ", billing.getSumm()).build());
		}
		billingDaoBeanOutList = billingDaoBean.getBillingOutList(bankAccountId);
		JsonArrayBuilder jArrayOut = Json.createArrayBuilder();
		for (Billing billing : billingDaoBeanOutList) {
			jArrayOut.add(Json.createObjectBuilder().add("id", billing.getId())
					.add("operation", billing.getOperationFrom().getName())
					.add("bankAccountFrom", billing.getBankAccountFrom().getNumber())
					.add("bankAccountTo", billing.getBankAccountTo().getNumber())
					.add("description", billing.getDescription()).add("summ", billing.getSumm()).build());
		}
		jsonObject = Json.createObjectBuilder().add("BillingInpuList", jArrayInput.build())
				.add("BillingOutList", jArrayOut.build()).build();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}

		System.out.println(stringWriter.toString());
		return jsonObject;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject setNewBilling(String request) {
		Long userId;
		Long bankAccountFromId;
		Long bankAccountToId;
		Long summ;
		String description;

		System.out.println(request);
		if (request.isEmpty() != true) {
			JsonReader jsonReader = Json.createReader(new StringReader(request));
			jsonObject = jsonReader.readObject();

			JsonObject jsonObjectInRequest = jsonObject.getJsonObject("NewBilling");
			userId = jsonObjectInRequest.getJsonNumber("userId").longValue();
			bankAccountFromId = jsonObjectInRequest.getJsonNumber("bankAccountFromId").longValue();
			bankAccountToId = jsonObjectInRequest.getJsonNumber("bankAccountToId").longValue();
			summ = jsonObjectInRequest.getJsonNumber("summ").longValue();
			description = jsonObjectInRequest.getJsonString("description").getString();

			billingDaoBean.saveNew(userId, bankAccountFromId, bankAccountToId, summ, description);

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
