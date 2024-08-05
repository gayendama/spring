insert into item_to_process (id, version, objet_id, second_objet_id,  montant)
	select public.uuid_generate_v4(), 0, f.id, b.id, b.montant_frais
	from
		type_compte a,
		frais_type_compte_cond_genre b,
		frais c,
		genre_client d,
		client e,
		compte f
	where
		a.id = b.type_compte_id
  		AND c.id = b.frais_id
  		AND c.code_frais = :codeFrais
  		AND d.id = e.genre_client_id
  		AND f.code_etat = 0
  		AND d.id = b.genre_client_id
  		AND a.id = f.type_compte_id
  		AND e.id = f.client_id
  		AND b.montant_frais > 0
	order by e.id
ON CONFLICT (objet_id) DO NOTHING;