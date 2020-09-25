package resource;

import multichain.VaccinationStream;
import pojo.VaccinationRecord;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/vaccination")
@Produces(MediaType.APPLICATION_JSON)
public class VaccinationResource {
    VaccinationStream vaccinationStream;

    public VaccinationResource(VaccinationStream vaccinationStream) {
        this.vaccinationStream = vaccinationStream;
    }

    @GET
    public List<VaccinationRecord> list() {
        return vaccinationStream.list();
    }
}
