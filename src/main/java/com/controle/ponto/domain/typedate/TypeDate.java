package com.controle.ponto.domain.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Table(name="typedates")
@Entity(name="typedates")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class TypeDate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    LocalTime time;

    char weekend;

    public TypeDate(TypeDateRequestDTO typeDateRequest){
        this.id = typeDateRequest.getId();
        this.name = typeDateRequest.getName();
        this.time = typeDateRequest.getTime();
        this.weekend = typeDateRequest.getWeekend();
    }
}
