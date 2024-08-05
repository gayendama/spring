INSERT INTO item_to_process (id, version, objet_id, second_objet_id)
	SELECT public.uuid_generate_v4(), 0, a.id, d.id
    FROM
      frais_client a,
      frais b,
      evenements_comptable c,
      compte d ,
      type_compte e
    WHERE
      a.frais_id = b.id
      AND a.evenements_comptable_id = c.id
      AND a.compte_id = d.id
      AND a.montant > 0
      AND b.ind_recouvrement_automatique = TRUE
      AND a.ind_paye = FALSE
      AND d.solde_compte >= (a.montant + e.solde_minimum)
      AND d.type_compte_id=e.id
      AND d.opposition=false
      AND d.ind_compte_bloque=false
ON CONFLICT (objet_id) DO NOTHING;