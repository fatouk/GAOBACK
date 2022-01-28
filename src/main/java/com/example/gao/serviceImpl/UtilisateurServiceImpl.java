/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.serviceImpl;

import com.example.gao.entities.Utilisateur;
import com.example.gao.repositories.UtilisateurRepository;
import com.example.gao.service.UtilisateurService;
import com.example.gao.utils.JwtUtils;
import com.example.gao.utils.ResponseWrapper;
import java.io.IOException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author fakaloga
 */
@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    @Autowired
    UtilisateurRepository userrepos;
    @Autowired
    UserLoginService userLoginService;
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
       Utilisateur userSave=userrepos.save(utilisateur);
       return userSave;
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return userrepos.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Utilisateur utilisateur) {
        userrepos.delete(utilisateur);
    }

    @Override
    public void deleteUtilisateurById(Long idutilisateur) {
        userrepos.deleteById(idutilisateur);
    }

    @Override
    public Utilisateur getUtilisateurById(Long idUtilisateur) {
//        return userrepos.findById(idUtilisateur).get();
         return userrepos.findByIdAndEtat(idUtilisateur,true);
       
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return userrepos.findAll();
    }

    @Override
    public Utilisateur getUtilisateurByEtat(Boolean etat) {
        return userrepos.findOneByEtat(etat);    }
    
     @Override
    public Utilisateur findbyloginbypassword(String login,String password){
        Utilisateur user=new Utilisateur();
        user=userrepos.findOneByloginbypassword(login,password);
        return user;
    }
     @Override
    public Utilisateur findById(Long id) {
        return  userrepos.findById(id).get();
    }

    @Override
    public boolean existsById(String login) {
        return userrepos.equals(login);
    }

    @Override
    public List<Utilisateur> findAllByEtat(boolean etat) {
        return userrepos.findAllByEtat(etat);    }
    
    
    
    
    @Override
    public ResponseWrapper login(String login,String password){
         try {
            Utilisateur userLDAP= userLoginService.login(new ADService.LoginForm(login, password));
           System.out.println("userLDAP++++++++"+userLDAP);
            if(userLDAP==null){
                return null; 
           }
            Utilisateur userLocal=userrepos.findOneBylogin(login);
            userLDAP.setId((userLocal==null?null:userLocal.getId()));
            userLDAP.setProfil((userLocal==null?userLDAP.getProfil():userLocal.getProfil()));
            userLDAP.setStatus((userLocal==null?userLDAP.getStatus():userLocal.getStatus()));
            userrepos.save(userLDAP);
            Map<String, Object> claims = new HashMap<>();
   
        claims.put("login", userLDAP.getLogin());
        claims.put("matricule", userLDAP.getMatricule());
        GrantedAuthority authorities =new SimpleGrantedAuthority(userLDAP.getProfil());
        claims.put("roles", authorities);
        Map<String, Object> ret = new HashMap<>();
        ret.put("token", JwtUtils.generateToken(userLDAP.getLogin(), claims));
        ret.put("user", userLDAP);
        return ResponseWrapper.success(ret);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }
      @Override
    public Utilisateur findbylogin(String login,String password){
        Utilisateur user=new Utilisateur();
        user=userrepos.findOneBylogin(login);
        return user;
    }
   @Override
     public void savefile(MultipartFile file)  {
        try {
//            System.out.println("je suis dans savefille");
//            Path root = Paths.get(uploadPath);
//            Path resolve = root.resolve(file.getOriginalFilename());
//            if (resolve.toFile()
//                       .exists()) {
//                System.out.println("File already exists: " + file.getOriginalFilename());
//            }
//            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            System.out.println("Could not store the file. Error: " + e.getMessage());
        }
    }
    @Override
     public void savefileExcel(List<Utilisateur> listeusers)  {
        try {
            System.out.println("je suis dans savefille");
            Path root = Paths.get(uploadPath);
            
        } catch (Exception e) {
            System.out.println("Could not store the file. Error: " + e.getMessage());
        }
    }
     
//     Workbook workbook;

	public List<Utilisateur> getExcelDataAsList() {

		List<String> list = new ArrayList<String>();

		// Create a DataFormatter to format and get each cell's value as String
//		DataFormatter dataFormatter = new DataFormatter();

		// Create the Workbook
//		try {
//			workbook = WorkbookFactory.create(new File(uploadPath));
//		} catch (EncryptedDocumentException | IOException e) {
//			e.printStackTrace();
//		}

		// Retrieving the number of sheets in the Workbook
//		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");
//
//		// Getting the Sheet at index zero
//		          Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
//		int noOfColumns = sheet.getRow(0).getLastCellNum();
//		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");
//
//		// Using for-each loop to iterate over the rows and columns
//		for (Row row : sheet) {
//			for (Cell cell : row) {
//				String cellValue = (cell.getStringCellValue());
//				list.add(cellValue);
//			}
//		}

		// filling excel data and creating list as List<Invoice>
//		List<Utilisateur> invList = createList(list, noOfColumns);
//
//		// Closing the workbook
//		try {
//			workbook.close();
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		return invList;
                return null;
	}

	private List<Utilisateur> createList(List<String> excelData, int noOfColumns) {

		ArrayList<Utilisateur> invList = new ArrayList<Utilisateur>();

		int i = noOfColumns;
		do {
			Utilisateur inv = new Utilisateur();

			inv.setNom(excelData.get(i));
                        inv.setPrenom(excelData.get(i+1));
			inv.setTelephone(excelData.get(i+2));
			inv.setEmail(excelData.get(i + 3));
			inv.setLogin(excelData.get(i + 4));

			invList.add(inv);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return invList;
	}

	@Override
	public int saveExcelData(List<Utilisateur> invoices) {
		invoices = userrepos.saveAll(invoices);
		return invoices.size();
	}
//    @Override
//    public ResponseWrapper login(String login,String password){
//         try {
//              Utilisateur userLocal=userrepos.findOneByloginbypassword(login,password);
////            Utilisateur userLDAP= userLoginService.login(new ADService.LoginForm(login, password));
////           System.out.println("userLDAP++++++++"+userLDAP);
//            if(userLocal==null){
//                return null; 
//           }
////            Utilisateur userLocal=userepos.findOneBylogin(login);
//            userLocal.setId((userLocal==null?null:userLocal.getId()));
//            userLocal.setProfil((userLocal==null?userLocal.getProfil():"user"));
////            userepos.save(userLDAP);
//            Map<String, Object> claims = new HashMap<>();   
//            claims.put("login", userLocal.getLogin());
//            claims.put("email", userLocal.getEmail());
//            
//            GrantedAuthority authorities =new SimpleGrantedAuthority(userLocal.getProfil());
//            claims.put("roles", authorities);
//            Map<String, Object> ret = new HashMap<>();
//            ret.put("token", JwtUtils.generateToken(userLocal.getLogin(), claims));
//            ret.put("user", userLocal);
//        return ResponseWrapper.success(ret);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//    }
}
