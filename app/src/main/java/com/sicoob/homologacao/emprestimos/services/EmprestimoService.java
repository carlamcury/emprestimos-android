package com.sicoob.homologacao.emprestimos.services;


import android.content.Context;

import com.sicoob.homologacao.emprestimos.Config;
import com.sicoob.homologacao.emprestimos.models.Emprestimo;
import com.sicoob.homologacao.emprestimos.tools.Resposta;

import java.util.List;

public class EmprestimoService {


    private static EmprestimoService instance;
    private String SERVER_URL;
    private Context context;

    private EmprestimoService(Context ctx) {
        context = ctx;
        SERVER_URL = Config.SERVER_URL;
    }

    public static EmprestimoService getInstance() {
        if (instance == null) {
            instance = new EmprestimoService(null);
        }
        return instance;
    }

    public static EmprestimoService getInstance(Context ctx) {
        if (instance == null) {
            instance = new EmprestimoService(ctx);
        }
        instance.context = ctx;
        return instance;
    }

    private static final String URL_PATH_GET_EMPRESTIMO = "emprestimo";
    private static final String URL_PATH_POST_EMPRESTIMO = "emprestimo";


    public List<Resposta> getEmprestimos() {
        return null;
    }

    public boolean registrar( Emprestimo emprestimo ) {
        return false;
    }

}