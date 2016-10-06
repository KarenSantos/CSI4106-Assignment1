package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fcost {
	private State current;

	private int AlgorithmChoice;
	private int cost;
	private Grid grid;
	private int parentCost;

		public Fcost(Grid grid,int AlgorithmChoice){
			
			this.AlgorithmChoice=AlgorithmChoice;

			this.cost=0;
			this.grid=grid;
			
		}
		
		public void setParentCost(int parentCost){
			
			this.parentCost=parentCost;
			
		}
		public void setState(State state){
			
			this.current=state;
		}
		
		public int getCost(){
			switch(AlgorithmChoice){
				case 1:
				case 2:
//						cost=0;
						cost=cumulativeCost();
						break;
				case 3:
						cost=heuristicCost()+current.getAction().getEngery();
						break;
				
				
			}
			return cost;
		}
		public int cumulativeCost(){
			return parentCost+current.getAction().getEngery();
		}
		
		/**
		 * Calculates the heuristic cost to be used for the A* search method in a current state
		 * @return The integer value of the heuristic.
		 * 
		 * 
		 * notice: there's one case that is not considered on this method:
		 * when we have more than one equal shortest distances(paths),
		 * it does not compare/find the min cost with (min # of obstacles)
		 * when this is the case, the corresponding node(path) will eventually 
		 * be dequeued among the children nodes form the fringe.
		 */			
		public int heuristicCost(){
			int herutisticCost;
			Position robotPosition=current.getRobotPos();
			Orientation currentOrientation=current.getOrientation();
			
			//deal with special 2 cases
			
			if(current.getDirtPositions().isEmpty()) 	herutisticCost=0;
			else if(current.getAction()==Action.SUCK){
				herutisticCost=0;
//				System.out.println("  -1numOBj : "+0+"\t"+robotPosition.toString()+"\t"+(herutisticCost+current.getAction().getEngery())+"\t"+currentOrientation+"\t"+current.getAction());
			}

			
			//	calculate possible shortest distance as well as # of obstacles in between
			
			else{
				List<Position>dirts=current.getDirtPositions();
				List<Position> distances = new ArrayList<>();

				Position shortest;
				int factor=current.getAction().Normalize();
	
				int index=-1;
				int numOfObs=0;
				int offset;
	
				
				for(Position dirt : dirts){
					distances.add(new Position(dirt.getX()-robotPosition.getX(),dirt.getY()-robotPosition.getY()));
				}
				Collections.sort(distances, Position.positionComparator);
				
				
				for(Position dis : distances){
					if(currentOrientation==Orientation.WEST){
						
						if(dis.getX()<=0){
							if(current.getAction()!=Action.MOVE){
								if(dis.getX()==0)continue;
							}
							index=distances.indexOf(dis);//for shortest distance
							
							// for possible obstacles in between based on the robot's direction
							for(int x=robotPosition.getX();x!=robotPosition.getX()+dis.getX();x--){
								if(!grid.isPositionAllowed(new Position(x,robotPosition.getY())))
									numOfObs++;
							}
							if(dis.getY()==0) offset=0;
							else if(dis.getY()<0) offset=-1;
							else offset=1;
							for(int y=robotPosition.getY()+offset;y!=robotPosition.getY()+dis.getY();y+=offset){
								
								if(!grid.isPositionAllowed(new Position(robotPosition.getX()+dis.getX(),y)))
									numOfObs++;
							}
							
							break;
						}
					}
					else if(currentOrientation==Orientation.EAST){
						if(dis.getX()>=0){
							if(current.getAction()!=Action.MOVE){
								if(dis.getX()==0)continue;
							}
							index=distances.indexOf(dis);
							
							for(int x=robotPosition.getX();x!=robotPosition.getX()+dis.getX();x++){
								if(!grid.isPositionAllowed(new Position(x,robotPosition.getY())))
									numOfObs++;
							}
							if(dis.getY()==0) offset=0;
							else if(dis.getY()<0) offset=-1;
							else offset=1;
							for(int y=robotPosition.getY()+offset;y!=robotPosition.getY()+dis.getY();y+=offset){
								
								if(!grid.isPositionAllowed(new Position(robotPosition.getX()+dis.getX(),y)))
									numOfObs++;
							}
							
							break;
						}
					}
					else if(currentOrientation==Orientation.NORTH){
						if(dis.getY()<=0){
							if(current.getAction()!=Action.MOVE){
								if(dis.getY()==0)continue;
							}
							index=distances.indexOf(dis);
							
							for(int y=robotPosition.getY();y!=robotPosition.getY()+dis.getY();y--){
								
								if(!grid.isPositionAllowed(new Position(robotPosition.getX(),y)))
									numOfObs++;
							}
							if(dis.getX()==0) offset=0;
							else if(dis.getX()<0) offset=-1;
							else offset=1;
							for(int x=robotPosition.getX()+offset;x!=robotPosition.getX()+dis.getX();x+=+offset){
								if(!grid.isPositionAllowed(new Position(x,robotPosition.getY()+dis.getY())))
									numOfObs++;
							}
							
							
							
							break;
						}
					}
					else {
						if(dis.getY()>=0){
							if(current.getAction()!=Action.MOVE){
								if(dis.getY()==0)continue;
							}
							index=distances.indexOf(dis);
							
							for(int y=robotPosition.getY();y!=robotPosition.getY()+dis.getY();y++){
								
								if(!grid.isPositionAllowed(new Position(robotPosition.getX(),y)))
									numOfObs++;
							}
							if(dis.getX()==0) offset=0;
							else if(dis.getX()<0) offset=-1;
							else offset=1;
							for(int x=robotPosition.getX()+offset;x!=robotPosition.getX()+dis.getX();x+=+offset){
								if(!grid.isPositionAllowed(new Position(x,robotPosition.getY()+dis.getY())))
									numOfObs++;
							}
							
							break;
						}
					}
				}
				if(index==-1){
					// upper bound of the ((cost of  (distance + # of obstacles))* # of dirts)*factors
					
					herutisticCost=((grid.getPossibleLongestDistance()+3*grid.getObstacles().size())*current.getDirtPositions().size())*factor;
					
					
//					System.out.println("  -1numOBj : "+numOfObs+"\t"+robotPosition.toString()+"\t"+(herutisticCost+current.getAction().getEngery())+"\t"+currentOrientation+"\t"+current.getAction());

				}
				else{

					shortest=distances.get(index);
					// upper bound of the ((cost of  (distance + # of obstacles))* # of dirts)*factors
					herutisticCost=((Math.abs(shortest.getX())+Math.abs(shortest.getY())+3*numOfObs)*current.getDirtPositions().size())*factor;
//					System.out.println("  NOT-1numOBj : "+numOfObs+"\t"+robotPosition.toString()+"\t"+shortest.toString()+"\t"+(herutisticCost+current.getAction().getEngery())+"\t"+currentOrientation+"\t"+current.getAction());

				}
			}
			
			
			
			
			return herutisticCost;
			
			
			
			

				
			
				
		}
		
		
		
}
