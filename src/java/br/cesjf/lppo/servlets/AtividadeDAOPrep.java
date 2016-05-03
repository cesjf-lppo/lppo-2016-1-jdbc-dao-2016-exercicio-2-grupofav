package br.cesjf.lppo.servlets;

import br.cesjf.lppo.Atividade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtividadeDAOPrep {
    private PreparedStatement operacaoListarTodos;
    private PreparedStatement operacaoCriar;
    private PreparedStatement operacaoExcluirPorId;

    public AtividadeDAOPrep() throws Exception {
        try {
            operacaoListarTodos = ConexaoJDBC.getInstance().prepareStatement("SELECT * FROM atividade");
            operacaoCriar = ConexaoJDBC.getInstance().prepareStatement("INSERT INTO atividade(funcionario, descricao, tipo, horas) VALUES(?, ?, ?, ?)", new String[]{"id"});
            operacaoExcluirPorId = operacaoExcluirPorId = ConexaoJDBC.getInstance().prepareStatement("DELETE FROM atividade WHERE id=?");

        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }
        
    List<Atividade> listaTodos() throws Exception {
        List<Atividade> todos = new ArrayList<>();
        try {
            ResultSet resultado = operacaoListarTodos.executeQuery();
            while (resultado.next()) {
                Atividade ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setFuncionario(resultado.getString("funcionario"));
                ativ.setDescricao(resultado.getString("descricao"));
                ativ.setTipo(resultado.getString("tipo"));
                ativ.setHoras(resultado.getInt("horas"));
                todos.add(ativ);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

        return todos;
    }

    void criar(Atividade novoAtiv) throws Exception {
        try {
            System.out.println("Antes de criar:" + novoAtiv);            
            operacaoCriar.setString(1, novoAtiv.getFuncionario());
            operacaoCriar.setString(2, novoAtiv.getDescricao());
            operacaoCriar.setString(3, novoAtiv.getTipo());
            operacaoCriar.setInt(4, novoAtiv.getHoras());
            operacaoCriar.executeUpdate();
            ResultSet keys = operacaoCriar.getGeneratedKeys();
            if (keys.next()) {
                novoAtiv.setId(keys.getLong(1));
            }
            System.out.println("Depois de criar:" + novoAtiv);
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    void excluirPorId(Long id) throws Exception {
        try {
            operacaoExcluirPorId.setLong(1, id);
            operacaoExcluirPorId.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    void excluir(Atividade ativ) throws Exception {
        excluirPorId(ativ.getId());
    }

    void salvar(Atividade ativ) throws Exception {
        Connection conexao = ConexaoJDBC.getInstance();
        Statement operacao = conexao.createStatement();
        try {
            operacao.executeUpdate(String.format("UPDATE atividade SET funcionario='%s', descricao='%s', tipo='%s', horas=%d WHERE id=%d", ativ.getFuncionario(), ativ.getDescricao(), ativ.getTipo(), ativ.getId()));
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
    
    Atividade buscaPorId(Long id) throws Exception {
        Atividade ativ = null;
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery(String.format("SELECT * FROM atividade WHERE id=%d", id));
            if (resultado.next()) {
                ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setFuncionario(resultado.getString("funcionario"));
                ativ.setDescricao(resultado.getString("descricao"));
                ativ.setTipo(resultado.getString("tipo"));
                ativ.setHoras(resultado.getInt("horas"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAOPrep.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
        return ativ;
    }
    
}