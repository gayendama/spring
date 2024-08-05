insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), o.id
	from operation as o
	inner join evenements_comptable as e ON e.id = o.evenements_comptable_id
	where e.id = :evenementsComptableId
		and o.statut_operation = :statutOperation
		and o.ind_extourne is not true
	order by o.numero_operation
ON CONFLICT (objet_id) DO NOTHING;