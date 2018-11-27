package com.noblapp.service;

import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class AssetService {

    private Logger logger = LoggerFactory.getLogger(AssetService.class);

    // 모든 리소스 경로는 아래 path + id + file
    private static final String categoryPath = "category/";
    private static final String pictogramPath = "pictogram/";
    private static final String placesPath = "places/";
    private static final String spotsPath = "spots/";
    private static final String eventsPath = "events/";
    private static final String mascotPath = "mascot/";

    @Value(value = "${assets.path}")
    private String assetsPath;

    @PostConstruct
    public void initService() {
        String[] paths = {categoryPath, pictogramPath, placesPath, spotsPath, eventsPath, mascotPath};

        for (String path : paths) {
            File file = new File(assetsPath, path);
            file.mkdirs();
        }
    }

    private String getExt(MultipartFile mfile) {
        String orgname = mfile.getOriginalFilename();
        return "."+orgname.substring(orgname.lastIndexOf(".")+1);
    }

    public String storeCategoryAssets(long cid, MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + categoryPath, Long.toString(cid));
        if (!dir.exists())
            dir.mkdirs();

        String ext = getExt(mfile);
//        ext = ext.substring(ext.lastIndexOf('/') + 1);
//        if (ext.contains("svg")) {
//            ext = ext.substring(0, ext.lastIndexOf('+'));
//        }

//		File outFile = new File(dir, String.format("%d.%s", System.currentTimeMillis(), ext));
        File outFile = new File(dir, UUID.randomUUID().toString() + ext);

        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public String storePlacesAssets(long pid, MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + placesPath, Long.toString(pid));
        if (!dir.exists())
            dir.mkdirs();

        String ext = getExt(mfile);

//        ext = "." + ext.substring(ext.lastIndexOf('/') + 1);
//        if (ext.contains("svg")) {
//            ext = ext.substring(0, ext.lastIndexOf('+'));
//        }

        File outFile;
        if (mfile.getName().startsWith("image")) {
            //outFile = new File(dir, "image_" + System.currentTimeMillis() + ext);
            outFile = new File(dir, "image_" + UUID.randomUUID().toString() + ext);
        } else if (mfile.getName().startsWith("video")) {
            //outFile = new File(dir, "video_" + System.currentTimeMillis() + ext);
            outFile = new File(dir, "video_" + UUID.randomUUID().toString() + ext);
        } else if (mfile.getName().equals("map_res_url")) {
            //outFile = new File(dir, "map_" + System.currentTimeMillis() + ext);
            outFile = new File(dir, "map_" + UUID.randomUUID().toString() + ext);
        } else if (mfile.getName().startsWith("other")) {
            //outFile = new File(dir, mfile.getOriginalFilename()); // 실제 파일 명
            outFile = new File(dir, "other_" + UUID.randomUUID().toString() + ext);
        } else if (mfile.getName().startsWith("input_custom_field")) {
            //outFile = new File(dir, mfile.getOriginalFilename()); // 실제 파일 명
            outFile = new File(dir, "cv_" + UUID.randomUUID().toString() + ext);
        } else {
            throw new NoblappException(ErrorCode.INCORRECT_FORMAT);
        }

        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public String storeSpotsAssets(long pid, MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + spotsPath, Long.toString(pid));
        if (!dir.exists())
            dir.mkdirs();

        String ext = getExt(mfile);
//        String ext = mfile.getContentType();
//        ext = "." + ext.substring(ext.lastIndexOf('/') + 1);
//        if (ext.contains("svg")) {
//            ext = ext.substring(0, ext.lastIndexOf('+'));
//        }

        File outFile;
        if (mfile.getName().equals("map_res_url")) {
            //outFile = new File(dir, "map_" + System.currentTimeMillis() + ext);
//            FileUtils.
            outFile = new File(dir, "map_" + UUID.randomUUID().toString() + ext);

        } else {
            throw new NoblappException(ErrorCode.INCORRECT_FORMAT);
        }

        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public String storeEventsAssets(long eid, MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + eventsPath, Long.toString(eid));
        if (!dir.exists())
            dir.mkdirs();

        String ext = getExt(mfile);
//        String ext = mfile.getContentType();
//        ext = "." + ext.substring(ext.lastIndexOf('/') + 1);

        File outFile;
        if (mfile.getName().equals("image")) {
            //outFile = new File(dir, "map_" + System.currentTimeMillis() + ext);
//            FileUtils.
            outFile = new File(dir, "event_" + UUID.randomUUID().toString() + ext);
        } else {
            throw new NoblappException(ErrorCode.INCORRECT_FORMAT);
        }

        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public String storePictogramAssets(long pid, MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + pictogramPath, Long.toString(pid));
        if (!dir.exists())
            dir.mkdirs();

        // 기존꺼 지우자...
        File[] prevFiles = dir.listFiles();
        if (prevFiles != null && prevFiles.length > 0) {
            for (File prevFile : prevFiles) {
                prevFile.delete();
            }
        }

        String ext = getExt(mfile);
//        String ext = mfile.getContentType();
//        ext = ext.substring(ext.lastIndexOf('/') + 1);
//        if (ext.contains("svg")) {
//            ext = ext.substring(0, ext.lastIndexOf('+'));
//        }
        //File outFile = new File(dir, String.format("%d.%s", System.currentTimeMillis(), ext));
        File outFile = new File(dir, UUID.randomUUID().toString() + ext);

        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public String storeMascotAssets(MultipartFile mfile) throws Exception {
        File dir = new File(assetsPath + mascotPath);
        if (!dir.exists())
            dir.mkdirs();

        String ext = getExt(mfile);
        File outFile = new File(dir, "mascot" + ext);
        if(outFile.exists()) {
            String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            System.out.println(today);
            outFile.renameTo(new File(dir, "mascot_" + today + ext));
            outFile = new File(dir, "mascot" + ext);
        }


        logger.info("writing to " + outFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(mfile.getBytes());
        fos.close();

        return outFile.getName();
    }

    public File getAsset(String subpath, String id, String file) {
        return new File(assetsPath + subpath + "/" + id + "/" + file);
    }

    public File getAssetCommon(String folder, String filename) {
        return new File("/opt/commons/" + folder + "/" + filename);
    }

    public File getPictogram(String id) {
        File path = new File(assetsPath + "pictogram/" + id);
        File[] files = path.listFiles();
        if (files != null && files.length > 0)
            return files[0];

        return null;
    }

    // 마스코트는 삭제 없음.
    public boolean deleteFile(String path, String id, String fileName) {
        String fullName = assetsPath ;

        if(path.equals("category")) {
            fullName += categoryPath;
        }else if(path.equals("pictogram")) {
            fullName += pictogramPath;
        }else if(path.equals("places")) {
            fullName += placesPath;
        }else if(path.equals("spots")) {
            fullName += spotsPath;
        }else if(path.equals("events")) {
            fullName += eventsPath;
        }

        fullName = fullName + id + "/" + fileName;

        File deleteFile = new File(fullName);
        return deleteFile.delete();
    }
}

