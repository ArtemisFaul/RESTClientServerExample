package applicaton;

import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.BankAccountDaoBean;
import entities.BankAccount;

@Path("bankAccountSearch")
public class SearchingBankAccount {
	@EJB
	private BankAccountDaoBean bankAccountDaoBean;
	private List<BankAccount> bankAccountDaoBeanList;
	private JsonObject jsonObject;

	@GET
	@Path("/{AccountNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getAccountList(@PathParam("AccountNum") long accountNum) {
		if (accountNum != 0) {

			bankAccountDaoBeanList = bankAccountDaoBean.searchAccount(accountNum);

			JsonObjectBuilder jBuilder = Json.createObjectBuilder();
			JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

			for (BankAccount bankAccount : bankAccountDaoBeanList) {
				jArrayBuilder.add(Json.createObjectBuilder().add("id", bankAccount.getId())
						.add("number", bankAccount.getNumber()).add("description", bankAccount.getDescription())
						.add("summ", bankAccount.getSumm()).build());
			}
			jsonObject = jBuilder.add("BankAccounts", jArrayBuilder.build()).build();

		}
		return jsonObject;
	}

	@GET
	@Path("/search/{AccountNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getAccountLikeList(@PathParam("AccountNum") long accountNum) {
		if (accountNum != 0) {

			bankAccountDaoBeanList = bankAccountDaoBean.searchAccountLike(accountNum);

			JsonObjectBuilder jBuilder = Json.createObjectBuilder();
			JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();

			for (BankAccount bankAccount : bankAccountDaoBeanList) {
				jArrayBuilder.add(Json.createObjectBuilder().add("id", bankAccount.getId())
						.add("number", bankAccount.getNumber()).add("description", bankAccount.getDescription())
						.add("summ", bankAccount.getSumm()).build());
			}
			jsonObject = jBuilder.add("BankAccounts", jArrayBuilder.build()).build();

		}
		return jsonObject;
	}
}
