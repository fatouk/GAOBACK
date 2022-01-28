/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author fakaloga
 */
@Component
public class Constant {
    
    public static final String DELETED="deleted";
    public static final String ACTIF="enable";
    public static final String INACTIF="disable";
    public static final String ADMIN="ADMIN";
    public static final String SUPER_ADMIN="SUPER_ADMIN";
    public static final String USER="USER";
    public static final String NBRE_DECLARATION="NDR";
    public static final String NBRE_DECLARATION_OML="DOML";
    public static final String NBRE_DECLARATION_OFMM="DOFMM";
    public static final String EFFECTIF_ORANGE="EF";
    public static final String EFFECTIF_OFMM="EOFMM";
    public static final String TAUX_ATTEINTE="TA ";
    public static final String TAUX_ATTEINTE_OFMM="TAOFMM";
    public static final String EFFECTIF_MANAGERS="EM";
    public static final String EFFECTIF_MANAGERS_OFMM="EMOFMM";
    public static final String NOMBRE_MANAGERS_AYANT_FAIT_LEUR_DECLARATION="NMD";
     public static final String NOMBRE_MANAGERS_AYANT_FAIT_LEUR_DECLARATION_OFMM="NMDOFMM";
    public static final String TAUX_ATTEINTE_MANAGERS="TAM";
    public static final String TAUX_ATTEINTE_MANAGERS_OFMM="TAMOFMM";
    public static final String NOMBRE_DECLARATIONS_SITUATIONS_OU_PONTIELLES_SITUATIONS_CONFLIT_INTERETS="NDPCI";
    public static final String CONFLITS_AVERES_DIRECTION="CAD";
    public static final String CONFLITS_AVERES_EMPLOYE="CAE";
    public static final String DIR_OML="OML";
    public static final String DIR_OFMM="OFMM";
    public static final String NBRE_DECLARATION_PREST_INTERIMAIRE="NDPI";
    public static final String D_OFMM="Aicha";
    public static final String TYPE_VARIABLE="V";
    public static final String TYPE_CONSTANT="C";
    public static String JKS_PATH;
    public static String JKS_PASSWORD;
    @Value("${orange.certificates.jks.path}")
    public void setJksPath(String jksPath) {
        JKS_PATH = jksPath;
    }
    @Value("${orange.certificates.jks.password}")
    public void setJksPassword(String jksPassword) {
        JKS_PASSWORD = jksPassword;
    }
}
