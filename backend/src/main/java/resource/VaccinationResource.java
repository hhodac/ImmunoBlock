package resource;

import dao.VaccinationDao;
import pojo.VaccinationRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/vaccination")
@Produces(MediaType.APPLICATION_JSON)
public class VaccinationResource {
    private final VaccinationDao vaccinationDao;

    public VaccinationResource(VaccinationDao vaccinationDao) {
        this.vaccinationDao = vaccinationDao;
    }

    @GET
    public String list() {
        return vaccinationDao.list();
    }

    @GET
    @Path("/{txid}")
    public String retrieve(@PathParam("txid") String txid) {
        return vaccinationDao.retrieve(txid);
    }

    @GET
    @Path("/search")
    public String searchByPassportNumber(@QueryParam("passportNumber") String passportNumber) {
        return vaccinationDao.searchByPassportNumber(passportNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewItem(VaccinationRecord vaccinationRecord) {
        return vaccinationDao.addNewItem(vaccinationRecord);
    }
}
