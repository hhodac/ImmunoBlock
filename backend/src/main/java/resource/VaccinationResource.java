package resource;

import dao.VaccinationDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public String getItem(@PathParam("txid") String txid) {
        return vaccinationDao.getItem(txid);
    }
}
