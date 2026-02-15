package modelo.enumeracao;

public enum EtapaDaObra {

    PROJETOS_SONDAGEM_LEGALIZACAO("Projetos, Sondagem e Legalização"),
    INSTALACOES_PROVISORIAS("Instalações Provisórias"),
    SERVICOS_PRELIMINARES("Serviços Preliminares"),
    CONSUMOS_MENSAIS("Consumos Mensais"),
    EQUIPE_DE_APOIO_DA_OBRA("Equipe de Apoio da Obra"),
    LIMPEZA_DA_OBRA("Limpeza da Obra"),
    EQUIPAMENTOS("Equipamentos"),

    SERVICOS_EM_TERRA("Serviços em Terra"),
    ESTAQUEAMENTO("Estaqueamento"),
    INFRAESTRUTURA("Infraestrutura"),
    DRENAGEM("Drenagem"),
    SUPRAESTRUTURA("Supraestrutura"),

    IMPERMEABILIZACAO("Impermeabilização"),
    ALVENARIA("Alvenaria"),
    COBERTURA("Cobertura"),

    REVESTIMENTO_DE_PAREDES("Revestimento de Paredes"),
    REVESTIMENTO_DE_PISOS("Revestimento de Pisos"),
    TETOS("Tetos"),
    REVESTIMENTO_EXTERNO("Revestimento Externo"),

    PINTURA_INTERNA("Pintura Interna"),
    PINTURA_EXTERNA("Pintura Externa"),

    ESQUADRIAS_ALUMINIO("Esquadrias de Alumínio"),
    ESQUADRIAS_VIDRO_TEMPERADO("Esquadrias de Vidro Temperado"),
    SERRALHERIA("Serralheria"),
    PORTAS("Portas"),

    INSTALACOES_ELETRICAS("Instalações Elétricas"),
    INSTALACOES_HIDROSANITARIAS_PREVENTIVA("Instalações Hidrossanitárias / Preventiva"),
    CLIMATIZACAO("Climatização"),
    ELEVADORES("Elevadores"),

    AREA_EXTERNA_MUROS("Área Externa / Muros"),
    MOBILIA_DECORACAO("Mobiliário e Decoração"),

    LIMPEZA_FINAL_DA_OBRA("Limpeza Final da Obra"),
    OMISSOS("Omissos");

    private final String descricao;

    EtapaDaObra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}