package it.polito.tdp.dizionario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DizionarioDAO {

	public List<String> searchWords(int n) {

		String sql = "SELECT nome " +
					"FROM parola " +
					"WHERE Length(nome) = ? ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n);
			
			ResultSet rs = st.executeQuery();
			
			List<String> parole = new ArrayList<String>();
			
			while( rs.next()) {
				parole.add(rs.getString("nome"));
			}
			
			return parole;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
