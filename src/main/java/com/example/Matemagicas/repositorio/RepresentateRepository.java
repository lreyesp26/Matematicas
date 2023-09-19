/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.repositorio;

import com.example.Matemagicas.modelos.Representate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Reyes
 */
@Repository
public interface RepresentateRepository extends JpaRepository<Representate, Long> {
    Representate findByCorreoelectronico(String correoelectronico);
}
