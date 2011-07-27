package controllers;

import java.util.List;

import models.CrowdGallery;
import play.mvc.*;

public class Galleries extends Controller {
	private static final int defaultPerPage = 10;
    public static void index() {
        List<CrowdGallery> galleries =  CrowdGallery.findAll();
        render(galleries);
    }

}
