package ar.gov.santafe.meduc.services.notificaciones;

import java.lang.reflect.ParameterizedType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.gov.santafe.meduc.services.notificaciones.util.Queries;

import dao.GenericDao;

@ApplicationScoped
public class ProductorUtil {

	@PersistenceContext(unitName = "SigaeEJBPU")
	private EntityManager em;
	
	@Produces
	public Queries getQueries() {
		return new Queries();
	}
	
/*
	@Produces
	public Gson getGsonParser() {
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gsonObj = b.create();
		return gsonObj;
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) 
	@Produces
	public <T> GenericDao<T> getDao(InjectionPoint p) { 
		Class clazz = null;
		if (((ParameterizedType) p.getType()).getActualTypeArguments()[0] instanceof Class){
			clazz = (Class) ((ParameterizedType) p.getType()).getActualTypeArguments()[0];
		}
		return new GenericDao(clazz,em);
	}
}