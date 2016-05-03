package br.cesjf.lppo.servlets;

import br.cesjf.lppo.Atividade;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AtividadeDAO {

    List<Atividade> listaTodos() throws Exception {
        List<Atividade> todos = new ArrayList<>();
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery("SELECT * FROM atividade");
            while (resultado.next()) {
                Atividade atividade = new Atividade();
                atividade.setId(resultado.getLong("id"));
                atividade.setFuncionario(resultado.getString("funcionario"));
                atividade.setDescricao(resultado.getString("descricao"));
                atividade.setTipo(resultado.getString("tipo"));
                atividade.setHoras(resultado.getInt("horas"));
                todos.add(atividade);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

        return todos;
    }

    void criar(Atividade novaAtividade) throws Exception {
        try {
            System.out.println("Antes de criar:" + novaAtividade);
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            operacao.executeUpdate(
                    String.format(
                            "INSERT INTO atividade(funcionario, descricao, tipo, horas) VALUES('%s','%s','%s','%s')",
                            novaAtividade.getFuncionario(),
                            novaAtividade.getDescricao(),
                            novaAtividade.getTipo(),
                            novaAtividade.getHoras()
                    ),
                    new String[]{"id"}
            );
            ResultSet keys = operacao.getGeneratedKeys();
            if (keys.next()) {
                novaAtividade.setId(keys.getLong(1));
            }
            System.out.println("Depois de criar:" + novaAtividade);
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    void excluirPorId(Long id) throws Exception {
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            operacao.executeUpdate("DELETE FROM atividade WHERE id=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            operacao.executeUpdate(String.format("UPDATE estabelecimento SET funcionario='%s', descricao='%s', tipo=%s WHERE id=%d", ativ.getFuncionario(), ativ.getDescricao(), ativ.getTipo(), ativ.getId()));
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
    
    Atividade buscaPorId(Long id) throws Exception {
        Atividade ativ = null;
        try {
            Connection conexao = ConexaoJDBC.getInstance();
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery(String.format("SELECT * FROM estabelecimento WHERE id=%d", id));
            if (resultado.next()) {
                ativ = new Atividade();
                ativ.setId(resultado.getLong("id"));
                ativ.setFuncionario(resultado.getString("fucionario"));
                ativ.setDescricao(resultado.getString("descricao"));
                ativ.setTipo(resultado.getString("tipo"));
                ativ.setHoras(resultado.getInt("hora"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtividadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
        return ativ;
    }
    
}