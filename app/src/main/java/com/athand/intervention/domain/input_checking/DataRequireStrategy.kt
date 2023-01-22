package com.athand.intervention.domain.input_checking
/**
 * Cree le 15/01/2023 par Gassama Souleyman
 *
 * J'ai crée cette interface "DataRequireStrategy" pour la class CheckValidityInputs
sauf erreur de ma part c'est le pattern strategie.

 * ----------------------------
 *    PROBLEMATIQUE RESOLUE
 * ----------------------------
 * 1) Au lieu de récupérer tous le viewmodel, je récupère juste ce dont j'ai besoin.
 * 2) "DataRequireStrategy" ou une class ou interface qui en a hérité, est implémenté dans plusieurs viewmodel.
 *    J'ai créé une class CheckValidityInputs avec comme paramètre constructeur
 *    l'interface "DataRequireStrategy".
 *
 * CONCLUSION:
 *    J'injecte les différents viewModel au constructeur CheckValidityInputs et ça crée une abstraction
 *    des donnés du viewmodel = j'ai uniquement accès aux data de l'interface
__________________________________________________________________________________________________

 * I created this "DataRequireStrategy" interface for CheckValidity Inputs class
 * Unless I'm mistaken, it's the strategy pattern.
 * ----------------------------
 *    ISSUE RESOLVED
 * ----------------------------
 *
 * 1) Instead of fetching all the viewmodel, I just fetch what I need.
 * 2) "DataRequireStrategy" or a class or interface that inherited from it, is implemented in multiple viewmodels.
 *    I have created a CheckValidityInputs class with constructor parameter
 *    the "DataRequireStrategy" interface.
 *
 * CONCLUSION:
 *    I inject the different viewModel to the CheckValidityInputs constructor and it creates an abstraction
 *    viewmodel data = I only have access to interface data
 */

interface DataRequireStrategy {

    interface CompanyDataRequire: DataRequireStrategy {
        fun get_Company_Name(): String
    }


    interface EmailDataRequire: DataRequireStrategy {
        fun get_Email(): String
    }

    interface ProfileDataRequire: DataRequireStrategy, EmailDataRequire {
        fun get_Firste_Name(): String
        fun get_Last_Name(): String
    }

    interface LoginDataRequire: DataRequireStrategy, EmailDataRequire {
        fun get_Password(): String
    }
}