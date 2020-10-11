package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.SRCVVariable;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SRCVVariableTest {

    @Test
    public void testFromTextInd() {

        String inatioText = "INATIO Caractère \n" +
                "Nationalité \n" +
                " Pas d'indication de nationalité \n" +
                "1 Française de naissance, y compris par réintégration \n" +
                "2 Française par naturalisation, mariage, déclaration ou option à sa majorité \n" +
                "3 Etrangère \n" +
                "4 Apatride \n" +
                "Champ : répondants au QI (+ de 16 ans) Pond. : PB040 ";

        List<String> lines = Stream.of(inatioText.split(System.lineSeparator())).collect(Collectors.toList());
        SRCVVariable inatio = SRCVVariable.fromLines(lines);
        System.out.println(inatio);
    }

}
