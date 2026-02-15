package modelo.enumeracao;

public enum Unidade {

	NADA("---"),
	UN("un"),
	M2("m²"),
	M3("m³"),
    ML("ml"),
    KG("kg"),
	SC("sc");

    private final String descricao;

    Unidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
	
}
