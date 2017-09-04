package kasper.rest.quotes;

import jsonMappers.QuoteId;
import com.google.gson.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.*;
import jsonMappers.*;

@Path("quote")
public class QuoteResource {

    @Context
    private UriInfo context;
    //Map is static, so it does not re-create on every run of the rest service.
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Random r = new Random();
    private static Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };
    private static int nextId = quotes.size() + 1;

    public QuoteResource() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getId(@PathParam("id") int id) {
        Quote q = new Quote(quotes.get(id));
        return gson.toJson(q);
    }

    @GET
    @Path("random")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandom() {
        Quote q = new Quote(quotes.get(r.nextInt(quotes.size())));
        return gson.toJson(q);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createNew(String content) {
        QuoteId q = gson.fromJson(content, QuoteId.class);
        q.setId(nextId);
        quotes.put(q.getId(), q.getQuote());
        nextId++;
        return gson.toJson(q);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putQuote(@PathParam("id") int id, String content) {
        Quote newQ = gson.fromJson(content, Quote.class);
        QuoteId returnQ = new QuoteId(id, newQ.getQuote());
        quotes.remove(id);
        quotes.put(id, newQ.getQuote());
        return gson.toJson(returnQ);
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteQuote(@PathParam("id") int id) {
        Quote q = new Quote(quotes.get(id));
        quotes.remove(id);
        return gson.toJson(q);
    }
}
