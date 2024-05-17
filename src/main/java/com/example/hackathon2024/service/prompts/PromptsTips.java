package com.example.hackathon2024.service.prompts;

import com.example.hackathon2024.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class PromptsTips {
    private final List<String> tips = Arrays.asList(
            "¿Sabías que...? Apagar las luces al salir de una habitación puede ahorrar hasta un 10% en tu factura de electricidad.",
            "Dato curioso: Utilizar regletas inteligentes para apagar dispositivos en espera puede ahorrar hasta 100 kWh al año.",
            "¡Increíble pero cierto! Bajar la temperatura del termostato en 1 grado Celsius puede reducir tu consumo de energía en un 7%.",
            "¿Sabías que...? Lavar la ropa con agua fría puede ahorrar hasta un 90% de la energía utilizada para calentar el agua.",
            "Dato curioso: Utilizar bombillas LED en lugar de incandescentes puede ahorrar hasta 75% de energía.",
            "Dato curioso: Secar la ropa al aire libre en lugar de usar la secadora puede ahorrar hasta 3 kWh por carga.",
            "¡Increíble pero cierto! Apagar la computadora en lugar de dejarla en modo de espera puede ahorrar hasta 40 kWh al año.",
            "¿Sabías que...? Desconectar los cargadores de dispositivos electrónicos cuando no se usan puede ahorrar hasta 10% de energía.",
            "Dato curioso: Utilizar la lavadora y el lavavajillas por la noche puede reducir la demanda de energía durante el día.",
            "¡Increíble pero cierto! Utilizar cortinas y persianas para bloquear el sol en verano puede reducir la necesidad de aire acondicionado.",
            "¿Sabías que...? Utilizar ventiladores de techo en lugar de aire acondicionado puede reducir el consumo de energía en un 40%.",
            "¡Increíble pero cierto! Cocinar con tapas en las ollas puede reducir el tiempo de cocción y ahorrar hasta un 30% de energía.",
            "Dato curioso: Utilizar el microondas en lugar del horno puede ahorrar hasta un 80% de energía.",
            "¿Sabías que...? Descongelar los alimentos en el refrigerador en lugar del microondas puede ahorrar energía y mantener la frescura de los alimentos.",
            "Dato curioso: Utilizar una ducha de bajo flujo puede reducir el consumo de agua caliente y ahorrar energía.",
            "Dato curioso: Usar el microondas en lugar del horno para calentar pequeñas porciones de comida puede ahorrar hasta un 80% de energía.",
            "Dato curioso: Compartir estos consejos con tus amigos y familiares puede ayudar a crear conciencia sobre el ahorro de energía y fomentar un estilo de vida más sostenible.",
            "¡Increíble pero cierto! Apagar los dispositivos electrónicos por la noche puede ahorrar energía y prolongar su vida útil.",
            "¿Sabías que...? Utilizar bombillas LED en lugar de incandescentes puede ahorrar hasta un 75% de energía.",
            "¿Sabías que...? Utilizar bombillas solares para iluminar el exterior de tu casa puede ser una opción ecológica y eficiente.",
            "Dato curioso: Utilizar un termostato programable puede reducir el consumo de energía y mantener una temperatura constante en tu hogar.",
            "¡Increíble pero cierto! Utilizar cortinas y persianas para bloquear el sol en verano puede reducir la necesidad de aire acondicionado.",
            "¿Sabías que...? Apagar las luces al salir de una habitación puede ahorrar hasta un 10% en tu factura de electricidad.",
            "Dato curioso: Participar en programas de respuesta a la demanda de energía puede ayudarte a ahorrar dinero y contribuir a la estabilidad de la red eléctrica.",
            "¡Increíble pero cierto! Utilizar electrodomésticos con certificación ENERGY STAR puede reducir el consumo de energía y proteger el medio ambiente.",
            "¿Sabías que...? Elegir un proveedor de energía verde que utilice fuentes renovables puede contribuir a un futuro más sostenible."

    );

    @Transactional(readOnly = true)
    public ApiResponse<String> getRandomTip() {
        if (tips.isEmpty()) {
            return new ApiResponse<>(null, true, HttpStatus.NOT_FOUND, "No hay tips disponibles");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(tips.size());
        String tip = tips.get(randomIndex);

        return new ApiResponse<>(tip, false, HttpStatus.OK, "Tip encontrado");
    }
}
