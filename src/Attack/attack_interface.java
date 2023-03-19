package Attack;

import Util.Coordinate;

public interface attack_interface {
    // the attack interface contains the two methods that are required for the attack process
    void update(Coordinate a);
    attack_result attack(Coordinate a);
}
