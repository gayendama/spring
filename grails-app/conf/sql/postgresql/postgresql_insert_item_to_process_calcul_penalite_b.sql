INSERT INTO item_to_process (id, objet_id)
	SELECT
	  public.uuid_generate_v4(),
	  id
	FROM
	  tableau_amortissement
	WHERE (statut_echeance = :echeanceImpaye OR (statut_echeance = :echeancePaiemetPartiel AND montant_penaliteapayer = 0))
	  AND nbj_retard < :limiteCalculPenalite
	  AND date_max_echeance < :dateTraitement
	ORDER BY credit_id, numero_echeance
ON CONFLICT (objet_id) DO NOTHING;