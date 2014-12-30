#ifndef JPINTYPE_H
#define JPINTYPE_H

/* Options for setting pin type */
#define PIN_TYPE_UNINITIALIZED      0   /* Pin hasn't been initialized. It is set to D Input by default */
#define PIN_TYPE_D_INPUT            1   /* Digitial Read will be called on this pin */
#define PIN_TYPE_A_INPUT            2   /* Analog Read will be called on this pin */
#define PIN_TYPE_D_INPUT_PULLUP     3   /* Digital Read will be calld on this pin */
#define PIN_TYPE_A_INPUT_PULLUP     4   /* Analog Read will be called on this pin */
#define PIN_TYPE_D_OUTPUT           5   /* Digial write will be called on this pin */
#define PIN_TYPE_A_OUTPUT           6   /* Digial write will be called on this pin */
#define PIN_TYPE_PING               7   /* Allows us to set a max distance so ping is fast for small distances */
#define PIN_TYPE_BLUETOOTH          8   /* Sets two pins, Tx and Rx */



#endif