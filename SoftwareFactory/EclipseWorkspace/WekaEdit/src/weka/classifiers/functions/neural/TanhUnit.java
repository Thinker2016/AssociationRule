package weka.classifiers.functions.neural;

import weka.core.RevisionHandler;
import weka.core.RevisionUtils;

/**
 * This can be used by the neuralnode to perform all it's computations (as a
 * tanh unit).
 *
 * @author Shao Wenzhao (shaowenzhao2011@139.com)
 * @version $Revision: 1000 $
 */
public class TanhUnit implements NeuralMethod, RevisionHandler {

	/**
	 * for serialization
	 */
	private static final long serialVersionUID = -576982527342413489L;

	/**
	 * Returns the revision string.
	 * 
	 * @return the revision
	 */
	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return RevisionUtils.extract("$Revision: 1000 $");
	}

	/**
	 * This function calculates what the output value should be.
	 * 
	 * @param node
	 *            The node to calculate the value for.
	 * @return The value.
	 */
	@Override
	public double outputValue(NeuralNode node) {
		// TODO Auto-generated method stub
		double[] weights = node.getWeights();
		NeuralConnection[] inputs = node.getInputs();
		double value = weights[0];
		for (int noa = 0; noa < node.getNumInputs(); noa++) {

			value += inputs[noa].outputValue(true) * weights[noa + 1];
		}

		// this I got from the Neural Network faq to combat overflow
		// pretty simple solution really :)
		if (value < -20) {
			value = -1;
		} else if (value > 20) {
			value = 1;
		} else {
			value = Math.tanh(value);
		}
		return value;
	}

	/**
	 * This function calculates what the error value should be.
	 * 
	 * @param node
	 *            The node to calculate the error for.
	 * @return The error.
	 */
	@Override
	public double errorValue(NeuralNode node) {
		// TODO Auto-generated method stub
		NeuralConnection[] outputs = node.getOutputs();
		int[] oNums = node.getOutputNums();
		double error = 0;

		for (int noa = 0; noa < node.getNumOutputs(); noa++) {
			error += outputs[noa].errorValue(true) * outputs[noa].weightValue(oNums[noa]);
		}
		double value = node.outputValue(false);
		error *= (1 + value) * (1 - value);

		return error;
	}

	/**
	 * This function will calculate what the change in weights should be and
	 * also update them.
	 * 
	 * @param node
	 *            The node to update the weights for.
	 * @param learn
	 *            The learning rate to use.
	 * @param momentum
	 *            The momentum to use.
	 */
	@Override
	public void updateWeights(NeuralNode node, double learn, double momentum) {
		// TODO Auto-generated method stub
		NeuralConnection[] inputs = node.getInputs();
		double[] cWeights = node.getChangeInWeights();
		double[] weights = node.getWeights();
		double learnTimesError = 0;
		learnTimesError = learn * node.errorValue(false);
		double c = learnTimesError + momentum * cWeights[0];
		weights[0] += c;
		cWeights[0] = c;

		int stopValue = node.getNumInputs() + 1;
		for (int noa = 1; noa < stopValue; noa++) {

			c = learnTimesError * inputs[noa - 1].outputValue(false);
			c += momentum * cWeights[noa];

			weights[noa] += c;
			cWeights[noa] = c;
		}
	}

}
