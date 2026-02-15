package modelo.enumeracao;

public enum TipoDeNaoConformidade {

    NAO_FINALIZADO("Não finalizado"),
    NECESSARIO_SUBSTITUICAO("Necessária substituição"),
    NECESSARIO_REFAZER("Necessário refazer"),
    NAO_APROVADO("Não aprovado"),
    DIFERENTE_DO_CONTRATADO("Diferente do contratado"),
    FALTA_EXECUTAR("Falta executar");

    private final String descricao;

    TipoDeNaoConformidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}