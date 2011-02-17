package model.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.BaseStation;
import model.SimulationMap;
import model.User;
import model.graph.Edge;

public class SCIP_FileOutputParser {
	
	public static boolean readSolutionAndEditTheMap( String fileOutputName, SimulationMap map ) {
		try {
			BufferedReader br = new BufferedReader( new FileReader( fileOutputName ) );
			
			String input;
			while( (input=br.readLine()) != null ) {
				if( input.startsWith("primal solution:") ) {
					br.readLine(); // "=============="
					br.readLine(); // ""
					input = br.readLine();
					if( input.startsWith("objective value:") ) {
						// TODO check the objective value
					}
					while( (input=br.readLine()).startsWith("a") ) {
						String[] str = input.split("#");	// expected: a#i#j#k ...
						int idx_u = Integer.parseInt(str[1]);
						int idx_b1 = Integer.parseInt(str[2]);
						int idx_b2 = Integer.parseInt(str[3].substring(0, 3).trim());
						str = str[3].substring(3).split("obj");
						double val = Double.parseDouble(str[0].substring(0, str[0].length()-1));
						if( val < 0.01 ) {
							continue;
						}
						// FIXME define indices for the vertices
						int i = 1;
						for( User user : map.getUsers() ) {
							if( i == idx_u ) {
								int j = 1;
								for( BaseStation base1 : map.getBasestations() ) {
									if( j == idx_b1 ) {
										Edge e_ub1 = map.getEdge(user, base1);
										e_ub1.getAttribute(map.getAssignmentKey()).setWeight(true);
										if( idx_b1 != idx_b2 ) {
											int k = 1;
											for( BaseStation base2 : map.getBasestations() ) {
												if( k == idx_b2 ) {
													Edge e_ub2 = map.getEdge(user, base2);
													e_ub2.getAttribute(map.getAssignmentKey()).setWeight(true);
													Edge e_b1b2 = map.getEdge(base1, base2);
													e_b1b2.getAttribute(map.getCooperationKey()).setWeight(true);
												}
												k++;
											}
										}
									}
									j++;
								}
							}
							i++;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
