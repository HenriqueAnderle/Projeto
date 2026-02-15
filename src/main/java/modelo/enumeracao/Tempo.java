package modelo.enumeracao;

public enum Tempo {

    BOM("Bom"),
    ESTAVEL("Estável"),
    INSTAVEL("Instável"),

    SECO("Seco"),
    UMIDO("Úmido"),

    CHUVOSO("Chuva"),
    MUITO_CHUVOSO("Muita Chuva"),

    VENTOSO("Ventoso"),
    FRIO("Frio"),
    QUENTE("Quente");

    private final String descricao;

    Tempo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}