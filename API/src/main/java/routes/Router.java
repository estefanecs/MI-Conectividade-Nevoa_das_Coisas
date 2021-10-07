/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routes;

import java.util.HashMap;

/**
 *
 * @author matheusnascimento
 */
public interface Router {
    
    public Object[] GET(Object body, HashMap data_base);
    public Object[] POST(Object body, HashMap data_base);
    public Object[] PUT(Object body, HashMap data_base);
    public Object[] DELETE(Object body, HashMap data_base);
    
}
