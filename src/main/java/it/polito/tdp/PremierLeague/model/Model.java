package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Match> idMap;
	private List<Arco> archi;
	private List<Match> percorsoBest;
	private Integer pesoBest;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}

	public String creaGrafo(Integer MIN, Integer mese) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<>();
		this.archi = new ArrayList<>();
		
		this.dao.listAllMatchesPerMonth(this.idMap, mese);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		this.archi = this.dao.getAllArchi(idMap, mese, MIN);
		for(Arco a : this.archi) {
			
			if(!this.grafo.containsEdge(a.getMatch1(), a.getMatch2())) {
				
				Graphs.addEdgeWithVertices(this.grafo, a.getMatch1(), a.getMatch2(), a.getPeso());
			}
		}
		
		return String.format("GRAFO CREATO!!\n\n#VERTICI: %s\n#ARCHI: %s\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public List<Arco> getConnessioneMax() {
		List<Arco> temp = new ArrayList<>();
		Arco arcoBest = this.archi.get(0);
		
		for(Arco a : this.archi) {
			if(a.getPeso()>arcoBest.getPeso())
				arcoBest = a;
		}
		
		temp.add(arcoBest);
		
		for(Arco a : this.archi) {
			if(a.getPeso()==arcoBest.getPeso() && !a.equals(arcoBest))
				temp.add(a);
		}
		
		return temp;
	}
	
	public List<Match> getMatchGrafo() {
		
		List<Match> temp = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(temp);
		return temp;
	}

	public void trovaPercorso(Match m1, Match m2) {
		this.percorsoBest = new ArrayList<>();
		this.pesoBest = 0;
		
		Integer pesoParziale = 0;
		List<Match> parziale = new ArrayList<>();
		parziale.add(m1);
		
		this.calcolaPercorso(parziale, pesoParziale, m1, m2);
	}

	private void calcolaPercorso(List<Match> parziale, Integer pesoParziale, Match precedente, Match destinazione) {
		
		if(precedente.equals(destinazione)) {
			if(pesoParziale>this.pesoBest) {
				this.percorsoBest = new ArrayList<>(parziale);
				this.pesoBest = pesoParziale;
			}
			
			return;
		}
		
		for(Match m : Graphs.neighborListOf(this.grafo, precedente)) {
			
			if(!(precedente.getTeamHomeID().equals(m.getTeamHomeID()) && precedente.getTeamAwayID().equals(m.getTeamAwayID())) 
					&& !(precedente.getTeamHomeID().equals(m.getTeamAwayID()) && precedente.getTeamAwayID().equals(m.getTeamHomeID()))
					&& !parziale.contains(m)) {
				
				parziale.add(m);
				pesoParziale += (int)this.grafo.getEdgeWeight(this.grafo.getEdge(precedente, m));
				this.calcolaPercorso(parziale, pesoParziale, m, destinazione);
				pesoParziale -= (int)this.grafo.getEdgeWeight(this.grafo.getEdge(precedente, m));
				parziale.remove(parziale.size()-1);		
			}
		}
	}
	
	public List<Match> getPercorso() {
		return this.percorsoBest;
	}
	
	public Integer getPesoPercorso() {
		return this.pesoBest;
	}
}
