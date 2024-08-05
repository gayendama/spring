INSERT INTO item_to_process (id, version, objet_id, second_objet_id)
	SELECT
	  public.uuid_generate_v4(),
	  0,
	  a.id,
	  b.id
	FROM
	  provision_pret a, taux_provision b
	WHERE a.date_arrete = :dateArrete
               AND a.ind_imputation = false
               AND a.nbre_jours_retard > b.duree_min
               AND b.duree_max >= a.nbre_jours_retard
               ORDER BY a.credit_id
ON CONFLICT (objet_id) DO NOTHING;