package com.sicoob.homologacao.emprestimos.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


    public class Resposta<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        public List<T> list;
        public HashMap<String, Object> mapJsonReturn;
        public T item;
        protected String msg;

        public Resposta(T[] _array) {
            List<T> lista = new ArrayList();
            Object[] var3 = _array;
            int var4 = _array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                T t = (T) var3[var5];
                lista.add(t);
            }

            this.list = lista;
            this.msg = "OK";
        }

        public Resposta(T t) {
            this.list = new ArrayList();
            this.list.add(t);
            this.msg = "OK";
        }

        public Resposta(List _list) {
            this.list = _list;
            this.msg = "OK";
        }

        public Resposta(HashMap<String, Object> map) {
            this.mapJsonReturn = map;
            this.msg = "OK";
        }

        public Resposta(List _list, String _msg) {
            this.list = _list;
            this.msg = _msg;
        }

        public Resposta(T object, String _msg) {
            this.item = object;
            this.msg = _msg;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getPrimeiroItem() {
            return this.list != null && this.list.size() > 0 ? this.list.get(0) : null;
        }

        public boolean temRetorno() {
            return this.list != null && this.list.size() > 0;
        }

        public boolean contemMsgErro() {
            return this.msg != null && this.msg.length() > 0 && !this.msg.equals("OK");
        }

        public boolean contemDados() {
            return this.list != null && this.list.size() > 0 || this.item != null && this.contemMsgErro();
        }
    }

