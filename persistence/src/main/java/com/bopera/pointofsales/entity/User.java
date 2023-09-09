package com.bopera.pointofsales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "osp_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "userpassword", nullable = false)
    private String userpassword;

    @Column(name = "lastmodule")
    private String lastmodule;

    @Column(name = "ordervolume")
    private Integer ordervolume;

    @Column(name = "language")
    private Integer language;

    @Column(name = "mobiletheme")
    private Integer mobiletheme;

    @Column(name = "receiptprinter")
    private Integer receiptprinter;

    @Column(name = "roombtnsize")
    private Integer roombtnsize;

    @Column(name = "tablebtnsize")
    private Integer tablebtnsize;

    @Column(name = "prodbtnsize")
    private Integer prodbtnsize;

    @Column(name = "prefertablemap")
    private Integer prefertablemap;

    @Column(name = "preferimgdesk")
    private Integer preferimgdesk;

    @Column(name = "preferimgmobile")
    private Integer preferimgmobile;

    @Column(name = "preferfixbtns")
    private Integer preferfixbtns;

    @Column(name = "showplusminus")
    private Integer showplusminus;

    @Column(name = "keeptypelevel", nullable = false)
    private Integer keeptypelevel;

    @Column(name = "tablesaftersend")
    private Integer tablesaftersend;

    @Column(name = "extrasapplybtnpos", nullable = false)
    private Integer extrasapplybtnpos;

    @Column(name = "calcpref")
    private Integer calcpref;

    @Column(name = "failedlogins")
    private String failedlogins;

    @Column(name = "active", nullable = false)
    private Integer active;

    @Column(name = "roleid")
    private Integer roleid;

    @Column(name = "area")
    private Integer area;

    @Column(name = "quickcash")
    private Integer quickcash;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "isowner")
    private Integer isowner;

}
