package modelo.enumeracao;

public enum Solicitante {

	MARCELO_ANDERLE("Marcelo Anderle"),
	ADEMAR_VIEIRA("Ademar Vieira");

    private final String descricao;

    Solicitante(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
	
}
