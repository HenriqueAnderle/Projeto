package modelo.enumeracao;

public enum CondicaoClimatica {

    ENSOLARADO("Ensolarado"),
    NUBLADO("Nublado"),
    ENCOBERTO("Encoberto"),

    CHUVA_LEVE("Chuva leve"),
    CHUVA_MODERADA("Chuva"),
    CHUVA_FORTE("Chuva forte"),
    GAROA("Garoa"),

    VENTO_FRACO("Vento fraco"),
    VENTO_FORTE("Vento forte"),

    NEBLINA("Neblina"),
    NEVOEIRO("Nevoeiro"),

    FRIO("Frio"),
    AMENO("Ameno"),
    QUENTE("Quente");

    private final String descricao;

    CondicaoClimatica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}