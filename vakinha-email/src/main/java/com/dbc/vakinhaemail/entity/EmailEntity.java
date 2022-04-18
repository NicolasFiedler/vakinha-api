package com.dbc.vakinhaemail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailEntity {
        private String username;
        private String ownerEmail;
        private String title;
        private Double goal;
}
