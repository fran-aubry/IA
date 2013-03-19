package Search;

import java.util.ArrayList;

import Functions.CutoffFunctionI;
import Functions.DefaultCutoffFunction;
import Functions.DefaultEvaluationFunction;
import Functions.DefaultSuccessorFunction;
import Functions.EvaluationFunctionI;
import Functions.SuccessorFunctionI;
import Go.Board;
import Go.Color;
import Go.Index;

public class AlphaBeta {

	private CutoffFunctionI cutoffFunc;
	private EvaluationFunctionI evalFunc;
	private SuccessorFunctionI successorFunc;
	private int alpha0;
	private int beta0;
	
	private ArrayList<Index> depthAction;
	
	public AlphaBeta() {
		alpha0 = Integer.MIN_VALUE;
		beta0 = Integer.MAX_VALUE;
		cutoffFunc = new DefaultCutoffFunction();
		evalFunc = new DefaultEvaluationFunction();
		successorFunc = new DefaultSuccessorFunction();
	}
	
	public AlphaBeta(int alpha, int beta) {
		alpha0 = alpha;
		beta0 = beta;
		cutoffFunc = new DefaultCutoffFunction();
		evalFunc = new DefaultEvaluationFunction();
		successorFunc = new DefaultSuccessorFunction();
	}
	
	public AlphaBeta(CutoffFunctionI cutoffFunction, EvaluationFunctionI evaluationFunction, SuccessorFunctionI successorFunc) {
		alpha0 = Integer.MIN_VALUE;
		beta0 = Integer.MAX_VALUE;
		this.cutoffFunc = cutoffFunction;
		this.evalFunc = evaluationFunction;
		this.successorFunc = successorFunc;
	}
	
	public AlphaBeta(int alpha, int beta, CutoffFunctionI cutoffFunction, EvaluationFunctionI evaluationFunction,  SuccessorFunctionI successorFunc) {
		alpha0 = alpha;
		beta0 = beta;
		this.cutoffFunc = cutoffFunction;
		this.evalFunc = evaluationFunction;
		this.successorFunc = successorFunc;
	}
	
	public void setStartingAlphaBeta(int alpha, int beta) {
		alpha0 = alpha;
		beta0 = beta;
	}
	
	public void setCutoffFunction(CutoffFunctionI cutoffFunc) {
		this.cutoffFunc = cutoffFunc;
	}
	
	public void setEvaluationFunction(EvaluationFunctionI evalFunc) {
		this.evalFunc = evalFunc;
	}
	
	public void setSuccessorFunction(SuccessorFunctionI successorFunc) {
		this.successorFunc = successorFunc;
	}
	
	public ArrayList<Index> alphaBeta(Board board, Color color) {
		depthAction = new ArrayList<Index>();
		alphaBeta(board, 0, alpha0, beta0, color);
		return depthAction;
	}
	
	private int alphaBeta(Board board, int depth, int alpha, int beta, Color color) {
		if(cutoffFunc.cutoff(board, depth)) {
			return evalFunc.evaluate(board, color);
		}
		if(depth % 2 == 0) { // the current player is MAX
			Index bestI = null;
			for(Index I : successorFunc.successors(board, color)) {
				Board successor = board.clone();
				successor.processMove(I, color);
				int val = alphaBeta(successor, depth + 1, alpha, beta, color.opposite());
				if(alpha < val) {
					alpha = val;
					bestI = I;
				}
				if(beta <= alpha) {
					//depthAction.add(bestI);
					break;
				}
			}
			depthAction.add(bestI);
			return alpha;
		} else { // the current player in MIN
			Index bestI = null;
			for(Index I : successorFunc.successors(board, color)) {
				Board successor = board.clone();
				successor.processMove(I, color);
				int val = alphaBeta(successor, depth + 1, alpha, beta, color.opposite());
				if(beta > val) {
					beta = val;
					bestI = I;
				}
				if(beta <= alpha) {
					//depthAction.add(bestI);
					break;
				}
			}
			depthAction.add(bestI);
			return beta;    
		}
	}
	
}

  