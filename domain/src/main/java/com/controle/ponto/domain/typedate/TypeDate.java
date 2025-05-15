package com.controle.ponto.domain.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.hour.Hour;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

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

    @OneToMany(mappedBy = "typeDate")
    private List<Hour> hour;

    public TypeDate(TypeDateRequestDTO typeDateRequest){
        this.id = typeDateRequest.getId();
        this.name = typeDateRequest.getName();
        this.time = typeDateRequest.getTime();
        this.weekend = typeDateRequest.getWeekend();
    }

    public static Duration parseNegativeTime(String timeString) {
        // Verifica se a string contém um sinal de negativo
        boolean isNegative = timeString.startsWith("-");
        if (isNegative) {
            timeString = timeString.substring(1); // Remove o sinal de negativo para processar
        }

        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        // Cria uma duração com base nas partes e ajusta se for negativa
        Duration duration = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);

        return isNegative ? duration.negated() : duration;
    }

    public static long durationInHours(String timeString){
        Duration duration = parseNegativeTime(timeString);

        return duration.toHours();
    }
}
