package com.athand.intervention.domain
/**
 * Cree le 28/12/2022 par Gassama Souleyman
 * La mise dans la pile du backstack avec nav controller m'est obscure.
 * ----------------------------
 *    PROBLEMATIQUE RESOLUE
 * ----------------------------
 * 1) Cette interface me permet à l'activité de demander au fragment si,
 *      - il doit destroy
 *      ou
 *      - juste laisse le retour par défaut.
 *
 * Stacking the backstack with nav controller is obscure to me.
 * ----------------------------
 *    ISSUE RESOLVED
 * ----------------------------
 * 1) This interface allows me the activity to ask the fragment if,
 *      - he must destroy
 *      Or
 *      - just leaves the default return.
 */
interface PressBack {
    fun press_Back_From_Activity(): Boolean
}