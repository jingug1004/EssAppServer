package com.noblapp.controller;

import com.noblapp.controller.support.AbstractController;
import com.noblapp.service.AssetService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/assets")
public class AssetsController extends AbstractController {
	
	@Autowired private AssetService asset;
	/**
	 * /pictogram/{pid}
	 */
	@RequestMapping(value="/pictogram/{id}", method=RequestMethod.GET)
	public void getImage(
			@PathVariable("id") String id,
			HttpServletResponse response,
			HttpServletRequest request
			) throws IOException {

		File f = asset.getPictogram(id);
	    if (f == null)
	    	return;
	    
	    String filename = f.getName();	    
	    System.out.println("pictogram filename?" + f.getAbsolutePath());
	    
	    String ext = filename.substring(filename.lastIndexOf('.') + 1);
	    
	    if(ext.equals("svg")) {
	    	OutputStream out = response.getOutputStream();
	    	out.write(FileUtils.readFileToByteArray(f));
	    	out.flush();
	    	out.close();
	    }else{
	    	BufferedImage bi = ImageIO.read(f);
		    OutputStream out = response.getOutputStream();
		    ImageIO.write(bi, ext, out);
		    out.close();
	    }
	}

	/**
	 * /category/{cid}/{filename}
	 * /places/{pid}/{filename}
	 * /spots/{pid}/{filename}
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value="/{subpath}/{id}/{filename:.+}", method=RequestMethod.GET)
	public void getImage(@PathVariable("subpath") String subpath,
			@PathVariable("id") String id,
			@PathVariable("filename") String filename,
			HttpServletResponse response,
			HttpServletRequest request
			) throws IOException {

		File f = asset.getAsset(subpath, id, filename);
		String ext = filename.substring(filename.lastIndexOf('.') + 1);
		
	    System.out.println("filename?" + f.getAbsolutePath());
	    if(ext.equals("svg") || ext.equals("html") || ext.equals("json")) {
	    	OutputStream out = response.getOutputStream();
	    	out.write(FileUtils.readFileToByteArray(f));
	    	out.flush();
	    	out.close();
	    	
	    }else{
		    BufferedImage bi = ImageIO.read(f);
		    OutputStream out = response.getOutputStream();
		    ImageIO.write(bi, ext, out);
		    out.close();
	    }
	}



	@RequestMapping(value="/noblCommon/{folder}/{filename:.*}", method=RequestMethod.GET)
	public void getCommons(@PathVariable("folder") String folder,
						  @PathVariable("filename") String filename,
						 HttpServletResponse response
	) throws IOException {
		File f = asset.getAssetCommon(folder, filename);
		OutputStream out = response.getOutputStream();
		if(f.exists()){
			String ext = filename.substring(filename.lastIndexOf('.') + 1);

			if(ext.equals("json")) {
				out.write(FileUtils.readFileToByteArray(f));
				out.flush();
				out.close();
			}else{
				BufferedImage bi = ImageIO.read(f);
				ImageIO.write(bi, ext, out);
				out.close();
			}
		}else{
			String file404 = "{\"status\":404}";
			out.write(file404.getBytes());
			out.flush();
			out.close();
		}
	}

}
