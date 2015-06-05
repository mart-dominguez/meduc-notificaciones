package ar.gov.santafe.meduc.services.notificacciones.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Dependent
public class ProductorUtil {

	@Produces
	public Gson getGsonParser() {
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gsonObj = b.create();
		return gsonObj;
	}

}
