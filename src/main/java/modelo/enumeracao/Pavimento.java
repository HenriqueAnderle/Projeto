package modelo.enumeracao;

public enum Pavimento {

    SUBSOLO("Subsolo"),
    TERREO("Térreo"),
    MEZANINO("Mezanino"),
    COBERTURA("Cobertura"),
    RESERVATORIO("Reservatório"),
    CASA_DE_MAQUINAS("Casa de Máquinas"),
    ANDAR_1("Andar 1"),
    ANDAR_2("Andar 2"),
    ANDAR_3("Andar 3"),
    ANDAR_4("Andar 4"),
    ANDAR_5("Andar 5"),
    ANDAR_6("Andar 6"),
    ANDAR_7("Andar 7"),
    ANDAR_8("Andar 8"),
    ANDAR_9("Andar 9"),
    ANDAR_10("Andar 10"),
    ANDAR_11("Andar 11"),
    ANDAR_12("Andar 12"),
    ANDAR_13("Andar 13"),
    ANDAR_14("Andar 14"),
    ANDAR_15("Andar 15"),
    ANDAR_16("Andar 16"),
    ANDAR_17("Andar 17"),
    ANDAR_18("Andar 18"),
    ANDAR_19("Andar 19"),
    ANDAR_20("Andar 20"),
    ANDAR_21("Andar 21"),
    ANDAR_22("Andar 22"),
    ANDAR_23("Andar 23"),
    ANDAR_24("Andar 24"),
    ANDAR_25("Andar 25");

    private final String descricao;

    Pavimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}