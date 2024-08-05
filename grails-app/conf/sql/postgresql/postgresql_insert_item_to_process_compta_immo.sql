INSERT INTO item_to_process (id, objet_id)
	SELECT public.uuid_generate_v4(), a.id
	FROM
	  amortissement_immo a , calcul_immo c
	WHERE
	  c.statut_calcul = 'VT'
	  AND a.calcul_immo_id = c.id
	  AND c.date_comptable_calcul = :dateArrete
	  ORDER BY a.numero_echeance, a.immobilisation_id
ON CONFLICT (objet_id) DO NOTHING;