package com.ssafy.finalproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @Column(name = "dongCode")
    private String dongCode;

    @Column(name = "sidoName")
    private String sidoName;

    @Column(name = "gugunName")
    private String gugunName;

    @Column(name = "dongName")
    private String dongName;

}
