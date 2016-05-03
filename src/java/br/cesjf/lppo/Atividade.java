package br.cesjf.lppo;

public class Atividade {
    private Long id;
    private String funcionario;
    private String descricao;
    private String tipo;
    private Integer horas;

    public Atividade() {
    }

    public Atividade(Long id, String funcionario, String descricao, String tipo, Integer horas) {
        this.id = id;
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.tipo = tipo;
        this.horas = horas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }
    
    
    
}
